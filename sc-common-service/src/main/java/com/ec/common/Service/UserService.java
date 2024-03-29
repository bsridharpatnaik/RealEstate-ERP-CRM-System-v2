package com.ec.common.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ec.common.Data.*;
import com.ec.common.Model.Tenant;
import com.ec.common.Model.UserTenantMapping;
import com.ec.common.Repository.TenantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ec.common.Filters.FilterAttributeData;
import com.ec.common.Filters.FilterDataList;
import com.ec.common.Filters.UserSpecifications;
import com.ec.common.Model.Role;
import com.ec.common.Model.User;
import com.ec.common.Repository.RoleRepo;
import com.ec.common.Repository.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepo uRepo;

    @Autowired
    RoleRepo rRepo;

    @Autowired
    TenantService tenantService;

    @Autowired
    TenantRepo tenantRepo;

    // private static final Logger log = LoggerFactory.getLogger(UserService.class);
    public User createUser(CreateUserData userData) throws Exception {
        validatePayload(userData);
        String username = userData.getUsername();
        String password = userData.getPassword();
        ArrayList<String> roles = userData.getRoles();

        if (uRepo.findUserByUsername(username).size() == 0) {

            User user = new User();
            Set<Role> roleset = new HashSet<Role>();

            for (String role : roles) {
                Role roleEntity = rRepo.findByName(role);
                if (roleEntity != null)
                    roleset.add(roleEntity);
            }
            if (roleset.size() < 1)
                throw new Exception("Role(s) not Found!");
            user.setUserName(username);
            user.setStatus(true);
            user.setRoles(roleset);
            user.setPassword(bCryptPassword(password));
            user.setPasswordExpired(false);

            Set<UserTenantMapping> tenantList = new HashSet<>();
            for (TenantUserDTO td : userData.getTenants()) {
                UserTenantMapping data = new UserTenantMapping();
                data.setAuthorization(td.getAuthorization());
                data.setTenant(tenantRepo.findById(td.getTenantName()).get());
                tenantList.add(data);
            }
            user.setTenantList(tenantList);
            uRepo.save(user);
            return user;
        } else {
            throw new Exception("User already exists");
        }
    }

    public void changePassword(ChangePasswordData payload) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ArrayList<User> users = uRepo.findUserByUsername(auth.getName());
        if (users.size() == 1) {
            String currentPassword = bCryptPassword(payload.getOldPassword());

            User user = users.get(0);

            boolean isPasswordMatch = passwordEncoder.matches(payload.getOldPassword(), user.getPassword());
            if (isPasswordMatch) {
                user.setPassword(bCryptPassword(payload.getNewPassword()));
                uRepo.save(user);
            } else
                throw new Exception("Current password in system does not match with current password entered");

        } else {
            throw new Exception("None or Multiple users found by username!");
        }
    }

    private void validatePayload(CreateUserData userData) throws Exception {
        if (userData.getTenants() == null)
            throw new Exception("Required field tenants missing from request!");
        if (userData.getRoles() == null)
            throw new Exception("Required field roles missing from request!");
        if (userData.getTenants().size() == 0)
            throw new Exception("Access to minimum one tenant is required!");
        if (userData.getRoles().size() == 0)
            throw new Exception("Please add atleast one role!");

        List<String> validTenants = tenantService.getValidTenantKeys();
        boolean validTeant = true;
        for (TenantUserDTO str : userData.getTenants()) {
            if (str.getTenantName() == null || str.getAuthorization() == null) {
                throw new Exception("Tenant Name or Authorization cannot be null.");
            }
            if (!validTenants.contains(str.getTenantName()))
                validTeant = false;
        }
        if (!validTeant)
            throw new Exception("Invalid tenant name found in request!");
    }

    private String convertListTocsv(ArrayList<String> tenants) {
        for (String tenant : tenants)
            tenant = tenant.trim();
        return String.join(",", tenants);
    }

    public String bCryptPassword(String password) {
        String bcyptedPassword;
        bcyptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return bcyptedPassword;
    }

    public UserListWithTypeAheadData fetchAll(Pageable pageable) {
        UserListWithTypeAheadData tpData = new UserListWithTypeAheadData();
        tpData.setUserDetails(uRepo.findAll(pageable));
        tpData.setRoles(rRepo.findRoleNames());
        tpData.setUsernames(uRepo.findUserNames());
        return tpData;
    }

    public User resetPassword(ResetPasswordData rpData) throws Exception {
        String username = rpData.getUsername();
        String password = rpData.getNewPassword();
        ArrayList<User> users = uRepo.findUserByUsername(username);
        if (users.size() == 1) {
            User user = users.get(0);
            user.setPassword(bCryptPassword(password));
            uRepo.save(user);
            return user;
        } else {
            throw new Exception("None or Multiple users found by username!");
        }
    }

    public User updateRolesForUser(UpdateRolesForUserData upRoleData) throws Exception {
        String username = upRoleData.getUsername();
        ArrayList<String> roles = upRoleData.getRoles();
        ArrayList<User> users = uRepo.findUserByUsername(username);
        if (users.size() == 1) {
            User user = users.get(0);
            Set<Role> roleset = new HashSet<Role>();

            for (String role : roles) {
                Role roleEntity = rRepo.findByName(role);
                if (roleEntity != null)
                    roleset.add(roleEntity);
            }
            user.setRoles(roleset);
            uRepo.save(user);
            return user;
        } else {
            throw new Exception("None or Multiple users found by username!");
        }
    }

    public UserReturnData fetchUserDetails() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = uRepo.findUserByUsername(auth.getName()).get(0);
        return new UserReturnData(user.getUserId(), user.getUserName(), fetchRolesFromSet(user.getRoles())
                ,fetchTenantFromSet(user.getTenantList()), user.getTenantList());
    }

    public UserReturnData fetchUserDetailsById(Long id) throws Exception {
        UserReturnData userReturnData = new UserReturnData();
        Optional<User> userOpt = uRepo.findById(id);
        if (!userOpt.isPresent())
            throw new Exception("User not found with ID -" + id);

        User user = userOpt.get();
        userReturnData.setUsername(user.getUserName());
        userReturnData.setId(user.getUserId());
        userReturnData.setRoles(fetchRolesFromSet(user.getRoles()));
        userReturnData.setAllowedTenants(findTenantsForUser(user.getUserName()));
        return userReturnData;
    }

    private List<String> fetchRolesFromSet(Set<Role> roleSet) {
        List<String> roles = new ArrayList<String>();
        for (Role role : roleSet) {
            roles.add(role.getName());
        }
        return roles;
    }

    public List<UserReturnData> fetchUserList() {
        List<UserReturnData> userReturnDataList = new ArrayList<UserReturnData>();
        List<User> userList = uRepo.findAll();
        for (User user : userList) {
            UserReturnData userReturnData = new UserReturnData(user.getUserId(), user.getUserName(),
                    fetchRolesFromSet(user.getRoles()), fetchTenantFromSet(user.getTenantList()), user.getTenantList());
            userReturnDataList.add(userReturnData);
        }
        return userReturnDataList;
    }

    private List<String> fetchTenantFromSet(Set<UserTenantMapping> tenantList) {
        List<String> allowedTenants = new ArrayList<>();
        for (UserTenantMapping ut : tenantList) {
            allowedTenants.add(ut.getTenant().getName());
        }
        return allowedTenants;
    }

    public UserListWithTypeAheadData findFilteredUsers(FilterDataList contactFilterDataList, Pageable pageable) {
        UserListWithTypeAheadData tpData = new UserListWithTypeAheadData();

        tpData.setUserDetails(getFilteredData(contactFilterDataList, pageable));
        tpData.setRoles(rRepo.findRoleNames());
        tpData.setUsernames(uRepo.findUserNames());
        return tpData;
    }

    public Page<User> getFilteredData(FilterDataList contactFilterDataList, Pageable pageable) {
        Specification<User> spec = fetchSpecification(contactFilterDataList);
        if (spec != null)
            return uRepo.findAll(spec, pageable);
        return uRepo.findAll(pageable);
    }

    private Specification<User> fetchSpecification(FilterDataList contactFilterDataList) {
        Specification<User> specification = null;
        for (FilterAttributeData attrData : contactFilterDataList.getFilterData()) {
            String attrName = attrData.getAttrName();
            List<String> attrValues = attrData.getAttrValue();

            Specification<User> internalSpecification = null;
            for (String attrValue : attrValues) {
                internalSpecification = internalSpecification == null
                        ? UserSpecifications.whereUsernameContains(attrValue)
                        : internalSpecification.or(UserSpecifications.whereUsernameContains(attrValue));
            }
            specification = specification == null ? internalSpecification : specification.and(internalSpecification);
        }
        return specification;
    }

    public User findSingleUserFromAll(long id) throws Exception {
        Optional<User> user = uRepo.findById(id);
        if (user.isPresent())
            return user.get();
        else
            throw new Exception("User ID not found");
    }

    public User updateUser(Long id, CreateUserData payload) throws Exception {
        validatePayload(payload);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (payload.getUsername().equalsIgnoreCase(auth.getName()))
            throw new Exception("Cannot update logged in user");

        User user = findSingleUserFromAll(id);
        String username = payload.getUsername();
        if (!user.getUserName().equals(payload.getUsername())) {
            if (uRepo.findUserNames().contains(username))
                throw new Exception("Username already exists");
        }

        ArrayList<String> roles = payload.getRoles();
        Set<Role> roleset = new HashSet<Role>();

        for (String role : roles) {
            Role roleEntity = rRepo.findByName(role);
            if (roleEntity != null)
                roleset.add(roleEntity);
        }
        if (roleset.size() < 1)
            throw new Exception("Please select atleast one role!");
        user.setUserName(username);
        user.setStatus(true);
        user.setRoles(roleset);

        Set<UserTenantMapping> tenantList = new HashSet<>();
        for (TenantUserDTO td : payload.getTenants()) {
            UserTenantMapping data = new UserTenantMapping();
            data.setAuthorization(td.getAuthorization());
            data.setTenant(tenantRepo.findById(td.getTenantName()).get());
            tenantList.add(data);
        }
        user.setTenantList(tenantList);

        if (payload.getPassword() != null && payload.getPassword() != "")
            user.setPassword(bCryptPassword(payload.getPassword()));
        user.setPasswordExpired(false);
        uRepo.save(user);
        return user;
    }

    public UserReturnData fetchUserDetailsByName(String name) throws Exception {
        UserReturnData userReturnData = new UserReturnData();
        ArrayList<User> userList = uRepo.findUserByUsername(name);
        if (userList.size() != 1)
            throw new Exception("User not found with username -" + name);

        User user = userList.get(0);
        userReturnData.setUsername(user.getUserName());
        userReturnData.setId(user.getUserId());
        userReturnData.setRoles(fetchRolesFromSet(user.getRoles()));
        userReturnData.setAllowedTenants(findTenantsForUser(user.getUserName()));
        return userReturnData;
    }

    public List<Tenant> findTenantsForCurrentUser() throws Exception {
        List<Tenant> tenantList = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        ArrayList<User> userList = uRepo.findUserByUsername(username);

        if (userList.size() != 1)
            throw new Exception("Zero or more than one users found with username " + username);

        User user = userList.get(0);
        for (UserTenantMapping ut : user.getTenantList()) {
            tenantList.add(ut.getTenant());
        }
        return tenantList;
    }

    public List<String> findTenantsForUser(String username) throws Exception {
        List<String> tenantList = new ArrayList<>();
        ArrayList<User> userList = uRepo.findUserByUsername(username);

        if (userList.size() != 1)
            throw new Exception("Zero or more than one users found with username " + username);

        User user = userList.get(0);
        for (UserTenantMapping ut : user.getTenantList()) {
            tenantList.add(ut.getTenant().getName());
        }
        return tenantList;
    }
}

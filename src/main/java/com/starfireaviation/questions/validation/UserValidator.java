/*
 *  Copyright (C) 2022 Starfire Aviation, LLC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.starfireaviation.questions.validation;

import com.starfireaviation.model.Role;
import com.starfireaviation.model.User;
import com.starfireaviation.questions.exception.AccessDeniedException;
import com.starfireaviation.questions.exception.ResourceNotFoundException;
import com.starfireaviation.questions.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;

/**
 * BaseValidator.
 */
@Slf4j
@Component
public class UserValidator {

    /**
     * DataService.
     */
    @Autowired
    private DataService dataService;

    /**
     * Validates access by an admin or instructor.
     *
     * @param principal Principal
     * @throws AccessDeniedException when principal user is not permitted to access
     *                               user info
     */
    public void accessAdminOrInstructor(final Principal principal) throws AccessDeniedException {
        empty(principal);
        final User loggedInUser = dataService.getUser(principal.getName());
        final Role role = loggedInUser.getRole();
        if (role != Role.ADMIN && role != Role.INSTRUCTOR) {
            log.warn(
                    String.format(
                            "%s throwing AccessDeniedException because role is [%s]",
                            "accessAdminOrInstructor()",
                            role));
            throw new AccessDeniedException("Current user is not authorized");
        }
    }

    /**
     * Validates access by an admin.
     *
     * @param principal Principal
     * @throws ResourceNotFoundException when principal user is not found
     * @throws AccessDeniedException     when principal user is not permitted to
     *                                   access user info
     */
    public void accessAdmin(final Principal principal) throws ResourceNotFoundException,
            AccessDeniedException {
        empty(principal);
        final User loggedInUser = dataService.getUser(principal.getName());
        final Role role = loggedInUser.getRole();
        if (role != Role.ADMIN) {
            log.warn(
                    String.format(
                            "%s throwing AccessDeniedException because role is [%s]",
                            "accessAdmin()",
                            role));
            throw new AccessDeniedException("Current user is not authorized");
        }
    }

    /**
     * Validates access by any authenticated user.
     *
     * @param principal Principal
     * @throws AccessDeniedException when principal user is not permitted to access
     *                               user info
     */
    public void accessAnyAuthenticated(final Principal principal) throws AccessDeniedException {
        empty(principal);
        final User loggedInUser = dataService.getUser(principal.getName());
        final Role role = loggedInUser.getRole();
        if (role != Role.ADMIN && role != Role.INSTRUCTOR && role != Role.STUDENT) {
            log.warn(
                    String.format(
                            "%s throwing AccessDeniedException because role is [%s]",
                            "accessAnyAuthenticated()",
                            role));
            throw new AccessDeniedException("Current user is not authorized");
        }
    }

    /**
     * Validates access by an admin, instructor, or the authenticated user.
     *
     * @param userId    User ID
     * @param principal Principal
     * @throws AccessDeniedException when principal user is not permitted to access
     *                               user info
     */
    public void accessAdminInstructorOrSpecificUser(final Long userId, final Principal principal)
            throws AccessDeniedException {
        empty(principal);
        final User loggedInUser = dataService.getUser(principal.getName());
        final Role role = loggedInUser.getRole();
        if (role != Role.ADMIN && role != Role.INSTRUCTOR && userId.longValue() != loggedInUser.getId().longValue()) {
            log.warn(
                    String.format(
                            "%s throwing AccessDeniedException because role is [%s] and userId "
                                    + "is [%s] and loggedInUser ID is [%s]",
                            "accessAdminInstructorOrSpecificUser()",
                            role,
                            userId,
                            loggedInUser.getId()));
            throw new AccessDeniedException("Current user is not authorized");
        }
    }

    /**
     * Determines if logged in user is an admin.
     *
     * @param principal Principal
     * @return admin user?
     */
    public boolean isAdmin(final Principal principal) {
        try {
            accessAdmin(principal);
            return true;
        } catch (AccessDeniedException | ResourceNotFoundException e) {
            return false;
        }
    }

    /**
     * Determines if logged in user is an authenticated user.
     *
     * @param userId    User ID
     * @param principal Principal
     * @return authenticated user?
     */
    public boolean isAuthenticatedUser(final Long userId, final Principal principal) {
        try {
            empty(principal);
            final User loggedInUser = dataService.getUser(principal.getName());
            if (userId == loggedInUser.getId()) {
                return true;
            }
        } catch (AccessDeniedException ee) {
            return false;
        }
        return false;
    }

    /**
     * Determines if logged in user is an admin or instructor.
     *
     * @param principal Principal
     * @return admin or instructor user
     */
    public boolean isAdminOrInstructor(final Principal principal) {
        if (principal == null) {
            return false;
        }
        try {
            accessAdminOrInstructor(principal);
            return true;
        } catch (AccessDeniedException ade) {
            return false;
        }
    }

    /**
     * Ensures principal is not null.
     *
     * @param principal Principal
     * @throws AccessDeniedException when principal is null
     */
    private static void empty(final Principal principal) throws AccessDeniedException {
        if (principal == null) {
            log.warn(
                    String.format("%s throwing AccessDeniedException because principal is %s", "empty()", principal));
            throw new AccessDeniedException("No authorization provided");
        }
    }
}

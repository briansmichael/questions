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

package com.starfireaviation.questions.controller;

import com.starfireaviation.common.model.ACS;
import com.starfireaviation.questions.service.ACSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/acs")
public class ACSController {

    /**
     * ACSService.
     */
    @Autowired
    private ACSService acsService;

    /**
     * Gets list of question IDs matching search criteria.
     *
     * @param groupId optional groupId
     * @param code optional ACS code
     * @return list of ACS IDs
     */
    @GetMapping
    public List<Long> getACS(@RequestParam(value = "groupId", required = false) final Long groupId,
                             @RequestParam(value = "code", required = false) final String code) {
        final List<Long> ids = new ArrayList<>();
        if (groupId != null) {
            final List<Long> acsGroupIds = acsService
                    .findByGroupId(groupId)
                    .stream()
                    .map(ACS::getId)
                    .collect(Collectors.toList());
            ids.addAll(acsGroupIds);
        }
        if (code != null) {
            final List<Long> acsCodeIds = acsService
                    .findByCode(code)
                    .stream()
                    .map(ACS::getId)
                    .collect(Collectors.toList());
            if (ids.isEmpty()) {
                ids.addAll(acsCodeIds);
            } else {
                ids.retainAll(acsCodeIds);
            }
        }
        return ids.stream().distinct().sorted().collect(Collectors.toList());
    }

    /**
     * Gets an ACS by ID.
     *
     * @param id ACS ID
     * @return ACS
     */
    @GetMapping(path = "/{id}")
    public ACS getACS(@PathVariable("id") final Long id) {
        return acsService.get(id);
    }

    /**
     * Saves an ACS.
     *
     * @param acs ACS
     * @return ACS
     */
    @PostMapping
    @PutMapping
    public ACS save(final ACS acs) {
        return acsService.save(acs);
    }

}

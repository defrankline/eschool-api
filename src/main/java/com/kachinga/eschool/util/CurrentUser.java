package com.kachinga.eschool.util;

import com.kachinga.eschool.config.UserContextService;
import com.kachinga.eschool.dto.LoggedInUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUser {
    private final UserContextService userContextService;

    public LoggedInUserDto get() {
        return userContextService.getCurrentUser();
    }

    public Long userId(){
        return this.get().getId();
    }

    public Long schoolId(){
        return this.get().getSchool().getId();
    }
}

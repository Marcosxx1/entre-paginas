package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.dto.MembersFromCommunityResponse;
import com.tcc.entrepaginas.service.member.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/list-members/{communityId}")
    @ResponseBody
    public List<MembersFromCommunityResponse> listAllCommunityMembers(@PathVariable("communityId") String communityId) {

        log.info(
                "MemberController - GET on /list-members/{communityId}; /list-members/{communityId} called with communityId: {}",
                communityId);

        return memberService.getMembersByCommunityId(communityId);
    }
}

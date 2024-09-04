package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.dto.MembersFromCommunityResponse;
import com.tcc.entrepaginas.service.member.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/list-members/{communityId}")
    @ResponseBody
    public List<MembersFromCommunityResponse> listAllCommunityMembers(@PathVariable("communityId") String communityId) {
        log.info("Request to list all members of community. Community ID: {}", communityId);

        List<MembersFromCommunityResponse> members = memberService.getMembersByCommunityId(communityId);

        log.info("Successfully retrieved {} members for Community ID: {}", members.size(), communityId);
        return members;
    }

    @DeleteMapping("/remove-member/{memberId}")
    @ResponseBody
    public void removeMemberFromCommunity(@PathVariable("memberId") String memberId, Authentication authentication) {
        log.info("Request to remove member from community. Member ID: {}", memberId);

        memberService.removeMemberFromCommunity(memberId, authentication);

        log.info("Successfully removed member. Member ID: {}", memberId);
    }

    @PutMapping("/update-member-authorities/{memberId}/{newComunnityRole}")
    @ResponseBody
    public void updateMemberAuthorities(
            @PathVariable("memberId") String memberId, @PathVariable("newComunnityRole") String newComunnityRole) {

        log.debug("Request parameters - memberId: {}, newComunnityRole: {}", memberId, newComunnityRole);

        memberService.updateMemberAuthorities(memberId, newComunnityRole);

        log.info(
                "Successfully updated member authorities for memberId: {} to new role: {}", memberId, newComunnityRole);
    }
}

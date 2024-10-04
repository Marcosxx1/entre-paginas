package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.dto.MembersFromCommunityResponse;
import com.tcc.entrepaginas.domain.entity.RoleCommunity;
import com.tcc.entrepaginas.service.member.MemberService;
import com.tcc.entrepaginas.service.rolecomunity.RoleCommunityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final RoleCommunityService roleCommunityService;

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

    @PostMapping("/update-member-authorities")
    @ResponseBody
    public void updateMemberAuthorities(
            @RequestParam("memberId") String memberId, @RequestParam("newComunnityRole") String newComunnityRole) {

        log.debug("Request parameters - memberId: {}, newComunnityRole: {}", memberId, newComunnityRole);

        memberService.updateMemberAuthorities(memberId, newComunnityRole);

        log.info(
                "Successfully updated member authorities for memberId: {} to new role: {}", memberId, newComunnityRole);
    }

    @PostMapping("/addMember")
    public String addMemberToCommunity(
            @RequestParam("communityId") String communityId, @RequestParam("userId") String userId,
            RedirectAttributes redirectAttributes) {

        log.info("Esta chegando aqui: {} to new role: {}", communityId, userId);

        memberService.addMemberToCommunity(communityId, userId, redirectAttributes);

        return "redirect:/community/" + communityId;
    }

    @GetMapping("/roles")
    @ResponseBody
    public List<RoleCommunity> getAllRoles() {
        return memberService.getAllRoles();
    }
}

package com.example.springcore.folder;

import com.example.springcore.folder.dto.FolderRequestDto;
import com.example.springcore.security.UserDetailsImpl;
import com.example.springcore.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;


    @PostMapping("api/folders")
    public List<Folder> addFolders(@RequestBody FolderRequestDto folderRequestDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        List<String> folderNames = folderRequestDto.getFolderNames();
        User user = userDetails.getUser();
        return folderService.addFolder(folderNames,user);
    }


    @GetMapping("api/folders")
    public List<Folder> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return folderService.getFolders(userDetails.getUser());
    }



}

package com.example.springcore.controller;

import com.example.springcore.exception.RestApiException;
import com.example.springcore.folder.Folder;
import com.example.springcore.folder.FolderService;
import com.example.springcore.folder.dto.FolderRequestDto;
import com.example.springcore.product.Product;
import com.example.springcore.security.UserDetailsImpl;
import com.example.springcore.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;


    @PostMapping("api/folders")
    public ResponseEntity<?> addFolders(@RequestBody FolderRequestDto folderRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<String> folderNames = folderRequestDto.getFolderNames();
        User user = userDetails.getUser();
        List<Folder> folders = folderService.addFolders(folderNames, user);
        return new ResponseEntity<>(folders, HttpStatus.OK);
    }


    @GetMapping("api/folders")
    public List<Folder> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return folderService.getFolders(userDetails.getUser());
    }


    @GetMapping("api/folders/{folderId}/products")
    public ResponseEntity<?> getProductsInFolder(
            @PathVariable Long folderId,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Page<Product> productsInFolder = folderService.getProductsInFolder(folderId, page - 1, size, sortBy, isAsc, userDetails.getUser());
        return new ResponseEntity<>(productsInFolder, HttpStatus.OK);
    }

//    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
//    public ResponseEntity<?> handleException(IllegalArgumentException ex){
//        RestApiException restApiException = new RestApiException();
//        restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
//        restApiException.setErrorMessage(ex.getMessage());
//        return new ResponseEntity<>(restApiException,HttpStatus.BAD_REQUEST);
//    }
}

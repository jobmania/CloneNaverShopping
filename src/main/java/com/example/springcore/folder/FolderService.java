package com.example.springcore.folder;

import com.example.springcore.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class FolderService {
    private final FolderRepository folderRepository;

    public List<Folder> addFolder(List<String> folderNames, User user) {
        List<Folder> folderList = new ArrayList<>();
        for (String folderName : folderNames) {
            Folder folder = new Folder(folderName, user);
            Folder saveFolder = folderRepository.save(folder);  // 하나씩 순회
            folderList.add(saveFolder);
        }

//        folderList = folderRepository.saveAll(folderList);      //saveAll  <<< 이렇게 한꺼번에 저장
        return folderList;
    }

    public List<Folder> getFolders(User user) {
       return folderRepository.findAllByUser(user);
    }
}

package com.example.springcore.folder;

import com.example.springcore.product.Product;
import com.example.springcore.product.ProductRepository;
import com.example.springcore.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final FolderRepository folderRepository;
    private final ProductRepository productRepository;

    public List<Folder> addFolder(List<String> folderNames, User user) {
        List<Folder> folderList = new ArrayList<>();

        List<Folder> existFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);

        for (String folderName : folderNames) {
//            Optional<Folder> byName = folderRepository.findByUserAndName(user, folderName); // DB를 많이 건든다 ㅠㅠㅠ;

            // 중복 폴더는 생성 x
            if(!isExistFolerName(folderName,existFolderList)){
                Folder folder = new Folder(folderName, user);
                folderList.add(folder);
//                Folder saveFolder = folderRepository.save(folder);  // 하나씩 순회
            }
        }

        folderList = folderRepository.saveAll(folderList);      //saveAll  <<< 이렇게 한꺼번에 저장
        return folderList;
    }

    public List<Folder> getFolders(User user) {
        return folderRepository.findAllByUser(user);
    }

    public Page<Product> getProductsInFolder(
            Long folderId,
            int page,
            int size,
            String sortBy,
            boolean isAsc,
            User user) {

        Long userId = user.getId();
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> pageProduct = productRepository.findAllByUserIdAndFolderList_Id(userId, folderId, pageable);
        return pageProduct;
    }

    private boolean isExistFolerName(String folderName, List<Folder> existFolderList) {
        for (Folder folder : existFolderList) {
            return folder.getName().equals(folderName);
        }
        return false;
    }
}

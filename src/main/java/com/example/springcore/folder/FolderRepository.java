package com.example.springcore.folder;

import com.example.springcore.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder,Long > {
    List<Folder> findAllByUser(User user);

    Optional<Folder> findByUserAndName(User user, String folderName);

    List<Folder> findAllByUserAndNameIn(User user, List<String> folderName);  // List로 ...
}

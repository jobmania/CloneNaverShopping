package com.example.springcore.folder;

import com.example.springcore.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder,Long > {
    List<Folder> findAllByUser(User user);
}

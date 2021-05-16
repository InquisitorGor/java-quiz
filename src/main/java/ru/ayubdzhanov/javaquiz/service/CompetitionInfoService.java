package ru.ayubdzhanov.javaquiz.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ayubdzhanov.javaquiz.dao.CompetitionInfoRepository;
import ru.ayubdzhanov.javaquiz.domain.CompetitionInfo;

import java.util.List;

@Service
public class CompetitionInfoService {
    @Autowired
    private CompetitionInfoRepository competitionInfoRepository;
    @Autowired
    private FileComponent fileComponent;
    @Autowired
    private CategoryService categoryService;

    public CompetitionInfo getCompetitionInfoByCategoryId(String categoryId) {
        return competitionInfoRepository.findByCategoryId(Long.parseLong(categoryId)).orElse(getEmptyCompetitionInfo());
    }

    private CompetitionInfo getEmptyCompetitionInfo() {
        CompetitionInfo competitionInfo = new CompetitionInfo();
        competitionInfo.setDescription(Strings.EMPTY);
        competitionInfo.setImageLink(Strings.EMPTY);
        return competitionInfo;
    }

    public void saveCompetitionInfo(String description, MultipartFile image, String categoryId) throws Exception {
        CompetitionInfo competitionInfo = competitionInfoRepository.findByCategoryId(Long.parseLong(categoryId)).orElse(getEmptyCompetitionInfo());
        competitionInfo.setDescription(description);
        competitionInfo.setImageLink(fileComponent.uploadFileAndGetLink(image, "competitionInfo"));
        if (competitionInfo.getId() == null) {
            competitionInfo.setCategory(categoryService.getCategoryById(categoryId));
        }
        competitionInfoRepository.save(competitionInfo);
    }

    public List<CompetitionInfo> findAll() {
        return competitionInfoRepository.findAll();
    }

}

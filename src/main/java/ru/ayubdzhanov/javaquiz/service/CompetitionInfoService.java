package ru.ayubdzhanov.javaquiz.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ayubdzhanov.javaquiz.dao.CompetitionInfoRepository;
import ru.ayubdzhanov.javaquiz.domain.CompetitionInfo;

import java.util.List;

@Service
@Slf4j
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

    public void deleteCompetitionDescriptionPicture(String competitionInfoId) throws Exception {
        CompetitionInfo competitionInfo = competitionInfoRepository.findById(Long.parseLong(competitionInfoId)).get();
        if (competitionInfo.getId() == null) throw new Exception("There is no competition description for this request");
        String[] split = competitionInfo.getImageLink().split("/");
        fileComponent.deleteFile(split[split.length - 1]);
        competitionInfo.setImageLink(null);
        competitionInfoRepository.save(competitionInfo);
    }

    public void deleteCompetitionInfo(String competitionInfoId) {
        try {
            try {
                deleteCompetitionDescriptionPicture(competitionInfoId);
            } catch (Exception ignored) {

            }
            competitionInfoRepository.deleteById(Long.parseLong(competitionInfoId));
        } catch (Exception ignored) {
            log.error("There are key restrictions");
        }
    }
}

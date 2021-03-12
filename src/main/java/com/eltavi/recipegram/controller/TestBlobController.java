package com.eltavi.recipegram.controller;

import com.eltavi.recipegram.entity.TestTableBlob;
import com.eltavi.recipegram.exception.BadRequestException;
import com.eltavi.recipegram.repository.TestTableBlobRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class TestBlobController {

    private final TestTableBlobRepository testRepository;

    @PostMapping("uploadPhoto")
    public void handleFileUpload(@RequestBody MultipartFile file){
        // only do the upload if user selected file to upload
        if (!file.isEmpty()) {
            try {
                //get worker from db
                TestTableBlob row = new TestTableBlob();
                //convert file input stream to byte array, so that we can store it into db.
                byte[] bytes = IOUtils.toByteArray(file.getInputStream());
                row.setPhotoContentLength(Long.valueOf(file.getSize()).intValue());
                row.setPhotoContentType(file.getContentType());
                row.setPhotoBlob(bytes);
                testRepository.save(row);
            } catch (Exception e) {
                throw new BadRequestException("You failed to upload => " + e.getMessage());
            }
        } else {
            throw new BadRequestException("You failed to upload");
        }
    }

    // This method allows you to load image from an url like the following
    // http://hostname/workers/photo/1
    // to display as an image in the html file, we can use
    // <img src="http://hostname/workers/photo/1" />
    /*@RequestMapping("/photo/{idNo}")
    public void download(@PathVariable("idNo") String idNo,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        try {

            TblWorker worker = tblWorkerService.findTblWorker(idNo);
            response.setHeader("Content-Disposition", "inline;filename="" +worker.getName()+ """);

            if(worker.getPhotoBlob() == null) {
                // if this worker doesn't have any photo, redirect to a default image.
                response.sendRedirect(request.getContextPath() + "/img/no_img_180.png");
            } else {
                // else display the photo by writing the byte array to the response.
                OutputStream out = response.getOutputStream();
                response.setContentType(worker.getPhotoContentType());
                response.setContentLength(worker.getPhotoContentLength());
                IOUtils.copy(new ByteArrayInputStream(worker.getPhotoBlob()), out);

                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}

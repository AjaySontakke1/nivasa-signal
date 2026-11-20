package com.nivasasignal.controller;

import com.nivasasignal.dto.SourceImportResponse;
import com.nivasasignal.service.SourceImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/source-import")
@RequiredArgsConstructor
public class SourceImportController {

    private final SourceImportService sourceImportService;

    @PostMapping("/properties/{propertyId}")
    @ResponseStatus(HttpStatus.CREATED)
    public SourceImportResponse importListings(
            @PathVariable Long propertyId,
            @RequestParam String city
    ) {
        int importedCount =
                sourceImportService.importListings(propertyId, city);

        return new SourceImportResponse(importedCount);
    }
}

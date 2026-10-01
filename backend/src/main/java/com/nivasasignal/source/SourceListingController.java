package com.nivasasignal.source;

import com.nivasasignal.source.dto.CreateSourceListingRequest;
import com.nivasasignal.source.dto.SourceListingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SourceListingController {

    private final SourceListingService sourceListingService;

    @PostMapping("/source-listings")
    public ResponseEntity<SourceListingResponse> createSourceListing(
            @Valid @RequestBody CreateSourceListingRequest request
    ) {
        SourceListingResponse response =
                sourceListingService.createSourceListing(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/properties/{propertyId}/source-listings")
    public List<SourceListingResponse> getSourceListings(
            @PathVariable Long propertyId
    ) {
        return sourceListingService.getListingsByPropertyId(propertyId);
    }
}

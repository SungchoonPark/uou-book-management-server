package com.gaon.bookmanagement.controller;

import com.gaon.bookmanagement.constant.dto.ApiResponse;
import com.gaon.bookmanagement.dto.request.BookPostReqDto;
import com.gaon.bookmanagement.dto.request.BorrowReqDto;
import com.gaon.bookmanagement.dto.response.BookDetailRespDto;
import com.gaon.bookmanagement.dto.response.BookPostRespDto;
import com.gaon.bookmanagement.dto.response.BorrowRespDto;
import com.gaon.bookmanagement.service.book.BookService;
import com.google.protobuf.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService;

    // 책 등록
    @PostMapping("/api/admin/book")
    public ResponseEntity<ApiResponse<BookPostRespDto>> bookPost(
            @RequestPart(value = "bookPostReqDto") @Valid BookPostReqDto bookPostReqDto,
            @RequestPart(value = "file")MultipartFile file
    ) {
        BookPostRespDto bookPostRespDto = bookService.bookPost(bookPostReqDto, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(bookPostRespDto, "Book Post Success!"));
    }

    // isbn 중복 조회
    @GetMapping("/api/admin/{isbn}/isbn")
    public ResponseEntity<ApiResponse<Boolean>> isbnDupCheck(@PathVariable String isbn) {
        bookService.isbnDuplicateCheck(isbn);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(Boolean.FALSE, "사용 가능한 ISBN 입니다."));
    }

    // 책 수정
    @PutMapping("/api/admin/book/{bookId}")
    public ResponseEntity<ApiResponse<BookPostRespDto>> bookEdit(
            @PathVariable Long bookId,
            @RequestPart(value = "bookPostReqDto", required = false) @Valid BookPostReqDto bookPostReqDto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        BookPostRespDto bookEditDto = bookService.bookEdit(bookId, bookPostReqDto, file);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(bookEditDto, "Book Edit Success!"));
    }

    // 책 조회

    //책 상세 조회
    @GetMapping("/api/book/{bookId}")
    public ResponseEntity<ApiResponse<BookDetailRespDto>> getDetailBook(@PathVariable Long bookId) {
        BookDetailRespDto detailBook = bookService.getDetailBook(bookId);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(detailBook, "Get Book Success!"));
    }
    // 책 삭제
    @DeleteMapping("/api/admin/book/{bookId}")
    public ResponseEntity<ApiResponse<Boolean>> bookDelete(@PathVariable Long bookId) {
        bookService.bookDelete(bookId);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(Boolean.TRUE, "Book Delete Success!"));
    }
    // 책 빌리기
    @PostMapping("/api/user/book")
    public ResponseEntity<ApiResponse<List<BorrowRespDto>>> borrowBook(List<BorrowReqDto> borrowReqDtoList) {
        List<BorrowRespDto> borrowRespDtos = bookService.borrowBook(borrowReqDtoList);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(borrowRespDtos, "Book Borrow Success!"));
    }
}

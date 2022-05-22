package com.example.board.controller;

import com.example.board.auth.MyUserDetail;
import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")//localhost:8080/board/write
    public String boardWriteForm(){
        return "boardwrite";
    }
    
    @PostMapping("board/writePro")
    public String boardWritePro(Board board, Model model, @RequestParam("file") MultipartFile file) throws Exception{
        //System.out.println("제목 : " + board.getTitle());
        //System.out.println("내용 : " + content);
        boardService.write(board, file);
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "message";
                
    }
    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword,
                            Authentication authentication){
        Page<Board> list = null;
        if(searchKeyword == null){
            list = boardService.boardList(pageable);
        }else{
            list = boardService.boardSearchList(searchKeyword, pageable);

        }


        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        MyUserDetail userDetail = (MyUserDetail)authentication.getPrincipal();  //userDetail 객체를 가져옴
        model.addAttribute("info", userDetail.getUsername());      //유저 이메일
        return "boardlist";
    }

    @GetMapping("/board/view") //localhost:8080/board/view?id=1
    public String boardView(Model model, Integer id){
        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){
        model.addAttribute("board", boardService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, MultipartFile file) throws Exception{

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardService.write(boardTemp, file);
        return "redirect:/board/list";
    }

}

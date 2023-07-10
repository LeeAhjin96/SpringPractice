package com.example.w5.service;

import com.example.w5.dao.MemberDAO;
import com.example.w5.domain.MemberVO;
import com.example.w5.dto.MemberDTO;
import com.example.w5.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

@Log4j2
public enum MemberService {
    INSTANCE;

    private MemberDAO dao;
    private ModelMapper modelMapper;

    MemberService() {
        dao = new MemberDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }

    public MemberDTO login(String mid, String mpw) throws Exception {
        MemberVO vo = dao.getWithPassword(mid, mpw);

        MemberDTO memberDTO = modelMapper.map(vo, MemberDTO.class);

        return memberDTO;
    }
}

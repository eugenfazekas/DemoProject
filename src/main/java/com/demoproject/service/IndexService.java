package com.demoproject.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demoproject.entity.Index;
import com.demoproject.repository.IndexRepository;

@Service
public class IndexService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private IndexRepository indexRepository;
	
	@Autowired
	public IndexService(IndexRepository indexRepository) {
		this.indexRepository=indexRepository;
	}

	@Autowired	
	public IndexRepository getIndexRepository() {
		return indexRepository;
	}

	@Autowired
	public void setIndexRepository(IndexRepository indexRepository) {
		this.indexRepository = indexRepository;
	}

	public List<Index> getIndex() {
		return indexRepository.findByOrderByIdDesc();
	}
	
	public String registerIndex(Index indexToRegister) {
			
			indexToRegister.setCreated(new Date());
			indexRepository.save(indexToRegister);
			log.debug("ez az Index ServiceBol "+ indexToRegister.toString());
			return "ok";
		}
	
	public Index searchtitle(String title) {
		return indexRepository.findByTitleOrderByIdDesc(title);
	}
		
	public void deleteindex (String title) {
		 indexRepository.findByTitle1(title);
	}

	public Index findByTitle(String title) {
		
		return indexRepository.findByTitle(title);
	}
	
}

import { Injectable } from '@angular/core';
import { ArticleRestDataSourceService } from '../rest-api/article-rest-data-source.service';
import { ArticleModel } from '../model/article.model';
import { LogService } from '../shared/services/log.service';


@Injectable()
export class ArticlesRepository {

  private articles: ArticleModel[] = [];
  private articleAuthors: Set<string> = new Set<string>();

  constructor(private articleRestDataSource: ArticleRestDataSourceService,private logservice: LogService) { 
    this.logservice.logDebugMessage(String('ArticlesRepository constructor: '));
    this.articleAuthors.add('All Authors');
          articleRestDataSource.getArticles().subscribe(
            res => {     
                let array = res;
                this.articles = array;
                for (let model of array) 
                this.articleAuthors.add(model.owner);      
                   },
            err => console.log('Error on getting Articles',err)
          );
  }

  getArticles(): ArticleModel[] {
    this.logservice.logDebugMessage(String('ArticlesRepository getArticles() '));
    return this.articles;
  }

  getArticleAuthors(): Set<string> {
    this.logservice.logDebugMessage(String('ArticlesRepository getArticleAuthors() '))
    return this.articleAuthors;
  }
  
  saveArticle(article: ArticleModel, image?: FormData) {
    this.logservice.logDebugMessage(String('ArticlesRepository saveArticle() '))
    this.articleRestDataSource.saveArticle(article).subscribe(
      res => this.articles.push(res),
      err => console.log(err)
    );
  }

  saveImage(image?: FormData) {
    this.logservice.logDebugMessage(String('ArticlesRepository saveImage() '))
    this.articleRestDataSource.saveImage(image).subscribe(
      res => console.log(res),
      err => console.log(err)
    );
  }

  deleteArticle(articleId: string){
    this.logservice.logDebugMessage(String('ArticlesRepository deleteArticle() '))
    this.articleRestDataSource.deleteArticle(articleId).subscribe(
      res => this.articles.splice(this.articles.findIndex(o => articleId == o.id ), 1),
    )
  }
}

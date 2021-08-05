import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { RESOURCE_URL } from './user-rest-data-source.service';
import { ArticleModel } from 'src/app/model/article.model';
import { Observable } from 'rxjs';
import { LogService } from '../shared/services/log.service';
import { LoggedUserService } from '../shared/services/logged-user.service';

@Injectable()
export class ArticleRestDataSourceService {

  private baseURL: string ;
  
  private param(param: string) {
        return new HttpParams().set('articleId', param)
  }

  constructor( private _http: HttpClient, @Inject(RESOURCE_URL) _baseURL: string, private loggedUserService: LoggedUserService, private logservice: LogService ) {
           this.baseURL = _baseURL;
           this.logservice.logDebugMessage(String('ArticleRestDataSourceService constructor: ')); 
   }

   getArticles(): Observable<ArticleModel[]> {
    this.logservice.logDebugMessage(String('ArticleRestDataSourceService getArticles() '));
    return this._http.get<ArticleModel[]>(`${this.baseURL}/article/findAllArticles`,{'headers': this.loggedUserService.getOptions()})
   }
   
   saveArticle(article: ArticleModel): Observable<ArticleModel> {
    this.logservice.logDebugMessage(String('ArticleRestDataSourceService saveArticle() '));
    return this._http.post<ArticleModel>(`${this.baseURL}/article/saveArticle`,article, {'headers': this.loggedUserService.getOptions()});
  }

   saveImage(image: FormData): Observable<string> {
    this.logservice.logDebugMessage(String('ArticleRestDataSourceService saveImage() '));
    return this._http.post(`${this.baseURL}/article/saveImage`,image, {'headers': this.loggedUserService.getOptions(), responseType: 'text'});
  }

   deleteArticle(articleId: string):Observable<string> {
    this.logservice.logDebugMessage(String('ArticleRestDataSourceService deleteArticle() '));
     return this._http.post(`${this.baseURL}/admin/deleteArticle`,{},{'headers': this.loggedUserService.getOptions(), 'params' : this.param(articleId) , responseType: 'text'})
   }
}
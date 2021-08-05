import { Pipe, PipeTransform } from '@angular/core';
import { ArticleModel } from 'src/app/model/article.model';

@Pipe({
  name: 'nxPageFilterPipe',
  pure: false
})
export class NxPageFilterPipe implements PipeTransform {

   transform(articles: ArticleModel[], articledPerPage: number, actualPage: number): ArticleModel[] {
    let articleIndexStart ;
    let articleIndexEnd ;
    articleIndexStart = articleIndexStart < articles.length - (actualPage * articledPerPage - articledPerPage) ? 
                                                  (actualPage * articledPerPage - articledPerPage) : ( (actualPage-1) * articledPerPage );

     articleIndexEnd = articleIndexStart + actualPage >= 0 ? articleIndexStart + articledPerPage : articles.length;

    return articles.slice(articleIndexStart, articleIndexEnd);
    }
}

import { Pipe, PipeTransform } from '@angular/core';
import { ArticleModel } from 'src/app/model/article.model';

@Pipe({
  name: 'nxSearchFilterPipe',
  pure: false
})
export class NxSearchFilterPipe implements PipeTransform {

    transform(articles: ArticleModel[], search: string): ArticleModel[] {
      return search == '' ? articles : articles.filter(
        p => {
          let title = p.title.toLowerCase();
          let content = p.content.toLowerCase();
          let owner = p.owner.toLowerCase();
              return  title.includes(search.toLowerCase()) || 
                      content.includes(search.toLowerCase()) || 
                      owner.includes(search.toLowerCase())  
             }
          );
      }
  }
import { Pipe, PipeTransform } from '@angular/core';
import { ArticleModel } from 'src/app/model/article.model';

@Pipe({
  name: 'nxCategoryFilterPipe',
  pure: true
})
export class NxCategoryFilterPipe implements PipeTransform {

  transform(articles: ArticleModel[],category: string): ArticleModel[] {
    return category == 'All Categories' ? articles : articles.filter(p => p.category == category);
   
}

}

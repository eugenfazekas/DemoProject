import { Pipe, PipeTransform } from '@angular/core';
import { ArticleModel } from 'src/app/model/article.model';

@Pipe({
  name: 'nxPublishedDateFilterPipe',
  pure: false
})
export class NxPublishedDateFilterPipe implements PipeTransform {

  transform(articles: ArticleModel[], startDate: string, endDate: string): ArticleModel[] {

    let startStringDate = new Date(startDate);
    let startEpochDate = startStringDate.getTime();

    let endStringDate = new Date(endDate);
    let endEpochDate = endStringDate.getTime();

    articles.sort((a,b) => b.published_date.localeCompare(a.published_date) )

    return startDate == null ? articles : articles.filter(p => {
            let articleDate = new Date(p.published_date);
            let articleEpochDate = articleDate.getTime();
              return articleEpochDate > startEpochDate  && articleEpochDate < endEpochDate;
    });
}

}

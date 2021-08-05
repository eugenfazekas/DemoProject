import { Injectable } from '@angular/core';
import { CategoriesRestDataSourceService } from '../rest-api/categories-rest-data-source.service';
import { Observable } from 'rxjs';
import { LogService } from '../shared/services/log.service';

@Injectable()
export class CategoriesRepository {

  private categories: string[] = [];

  constructor( private categoriesRestDataSourceService: CategoriesRestDataSourceService,private logservice: LogService ) {  
    this.logservice.logDebugMessage(String('CategoriesRepository constructor: ')); 
                  this.categoriesRestDataSourceService.getCategories().subscribe(
          res => {
            this.categories.push('All Categories');
              for(let category of res){
              this.categories.push(category);
            }},
          err => console.log('Error on getting categories: ',err)
        )
  }

    addCategory(category: string) {
      this.logservice.logDebugMessage(String('CategoriesRepository addCategory() '));
        this.categoriesRestDataSourceService.addCategory(category).subscribe(
          res => this.categories.push(res),
          err => console.log('Error on adding category: ',err)
        )
      }

    deleteCategory(category: string) {
      this.logservice.logDebugMessage(String('CategoriesRepository getArtdeleteCategoryicles() '));
      this.categoriesRestDataSourceService.deleteCategory(category).subscribe(
        res => this.categories.splice(this.categories.findIndex(o => category == o ), 1),
        err => console.log('Error on deleting category: ',err)
         )
      }

      getCategories(): string[] {
        this.logservice.logDebugMessage(String('CategoriesRepository getCategories() '));
        return this.categories;
        }

}

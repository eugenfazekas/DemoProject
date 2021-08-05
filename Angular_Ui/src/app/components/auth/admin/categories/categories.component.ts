import { Component } from '@angular/core';
import { CategoriesRepository } from 'src/app/repository/categories-repository';
import { FormBuilder, Validators } from '@angular/forms';
import { LogService } from 'src/app/shared/services/log.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent {

  constructor(private categoriesRepository: CategoriesRepository, private formBuilder: FormBuilder, private logservice: LogService) {
    this.logservice.logDebugMessage(String('CategoriesComponent constructor: admin = '));
  }
  
  getCategories(): string[] {
    this.logservice.logDebugMessage(String('CategoriesComponent getCategories()'));
    return this.categoriesRepository.getCategories().filter(o => o != 'All Categories');
  }

  categoryForm = this.formBuilder.group({ category: ['',[Validators.required, Validators.minLength(3) ]]});
  
  onSubmit() {
    if(this.categoryForm.valid){
      this.logservice.logDebugMessage(String('CategoriesComponent onSubmit()'));
      this.categoriesRepository.addCategory(this.categoryForm.controls['category'].value)
    }
  };

  deleteCategory(item: string) {
    this.logservice.logDebugMessage(String('CategoriesComponent deleteCategory()'));
    this.categoriesRepository.deleteCategory(item);
  }
}

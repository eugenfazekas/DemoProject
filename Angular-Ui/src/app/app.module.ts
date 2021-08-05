import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './components/no_auth/user/login/login.component';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from  '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RegistrationComponent } from './components/no_auth/user/registration/registration.component';
import { HeaderComponent } from './components/no_auth/header/header/header.component';
import { UserRestDataSourceService, AUTH_URL, RESOURCE_URL } from './rest-api/user-rest-data-source.service';
import { NxRouting } from './app.routing';
import { AuthService } from './shared/services/auth.service';
import { AuthGuard} from './shared/guards/auth-guard';
import { NxMaterialModule } from './shared/nx-material/nx-material.module';
import { MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import { NgxMatFileInputModule } from '@angular-material-components/file-input';
import { LogLevel, LogService, } from './shared/services/log.service';
import { HomeComponent } from './components/auth/user/home/home.component';
import { FirstStepsComponent } from './components/auth/user/first-steps/first-steps.component';
import { UserRepository } from './repository/user-repository';
import { ArticleRestDataSourceService } from './rest-api/article-rest-data-source.service';
import { CategoriesRestDataSourceService } from './rest-api/categories-rest-data-source.service';
import { CategoriesRepository } from './repository/categories-repository';
import { CategoriesComponent } from './components/auth/admin/categories/categories.component';
import { ArticlesRepository } from './repository/articles-repository';
import { NxCategoryFilterPipe } from './shared/pipes/nx-article-category-filter.pipe';
import { NxAuthorFilterPipe } from './shared/pipes/nx-article-author-filter.pipe';
import { NxSearchFilterPipe } from './shared/pipes/nx-article-search-filter.pipe';
import { NxPublishedDateFilterPipe } from './shared/pipes/nx-article-published-date-filter.pipe';
import { EditUserDetailsComponent } from './components/auth/user/edit-user-details/edit-user-details.component';
import { NxPageFilterPipe } from './shared/pipes/nx-article-page-filter.pipe';
import { ImageService } from './shared/services/image.service';
import { ManageImagesComponent } from './components/auth/user/manage-images/manage-images.component';
import { ArticlesComponent } from './components/auth/common/articles/articles.component';
import { LoggedUserService } from './shared/services/logged-user.service';
import { AdminGuard } from './shared/guards/admin-guard';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    HeaderComponent,
    ArticlesComponent,
    HomeComponent,
    CategoriesComponent,
    NxCategoryFilterPipe,
    NxAuthorFilterPipe,
    NxSearchFilterPipe,
    NxPublishedDateFilterPipe,
    NxPageFilterPipe,
    EditUserDetailsComponent,
    ManageImagesComponent,
    FirstStepsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NxMaterialModule,
    NgxMatFileInputModule,
    NxRouting
  ],
  providers: [
              UserRestDataSourceService,
              { provide: AUTH_URL, useValue: 'http://localhost:4800'},
              { provide: RESOURCE_URL, useValue: 'http://localhost:8080'},
              AuthService,
              AuthGuard,
              AdminGuard,
              LoggedUserService,
              ArticleRestDataSourceService,
              ArticlesRepository,
              CategoriesRestDataSourceService,
              CategoriesRepository,
              { provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: { appearance: 'fill' } },
              { provide: LogLevel, useValue: LogLevel.INFO },
              { provide: LogService, 
                deps: [LogLevel],
              useFactory: (level) => {
                  let logger = new LogService();
                  logger.minimumLevel = level;
                  return logger;
              } 
            },
            UserRepository,
            ImageService
            ],
  bootstrap: [AppComponent]
})
export class AppModule { }

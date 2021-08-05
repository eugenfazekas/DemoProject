import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/no_auth/user/login/login.component';
import { RegistrationComponent } from './components/no_auth/user/registration/registration.component';
import { HomeComponent } from './components/auth/user/home/home.component';
import { AuthGuard } from './shared/guards/auth-guard';
import { FirstStepsComponent } from './components/auth/user/first-steps/first-steps.component';
import { ArticlesComponent } from './components/auth/common/articles/articles.component';
import { CategoriesComponent } from './components/auth/admin/categories/categories.component';
import { ManageImagesComponent } from './components/auth/user/manage-images/manage-images.component';
import { EditUserDetailsComponent } from './components/auth/user/edit-user-details/edit-user-details.component';
import { AdminGuard } from './shared/guards/admin-guard';


const routes: Routes = [
        { path: "", component: HomeComponent , canActivate: [AuthGuard] },
        { path: "firstSteps", component: FirstStepsComponent , canActivate: [AuthGuard] },
        { path: "home", component: HomeComponent , canActivate: [AuthGuard] },
        { path: "login", component: LoginComponent },
        { path: "registration", component: RegistrationComponent },
        { path: "articles", component: ArticlesComponent ,canActivate: [AuthGuard] },
        { path: "categories", component: CategoriesComponent, canActivate: [AdminGuard] },
        { path: "editUserProfile", component: EditUserDetailsComponent, canActivate: [AuthGuard] },
        { path: "manageImages", component: ManageImagesComponent, canActivate: [AuthGuard] }
      ]

export const NxRouting = RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' });
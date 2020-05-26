import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SearchTabComponent} from './search-tab/search-tab.component';
import {ProjectTabComponent} from './project-tab/project-tab.component';
import {AuthGuardService} from './services/auth-guard.service';
import {LoginComponent} from './login/login.component';
import {PublishTabComponent} from './publish-tab/publish-tab.component';
const routes: Routes = [
  {path: 'projects', component: ProjectTabComponent, canActivate: [AuthGuardService]},
  {path: 'search', component: SearchTabComponent, canActivate: [AuthGuardService]},
  {path: 'publish', component: PublishTabComponent, canActivate: [AuthGuardService]},
  {path: 'login', component: LoginComponent},
  {path: '**', redirectTo: '/login', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

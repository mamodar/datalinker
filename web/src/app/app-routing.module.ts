import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SearchTabComponent} from './home/search-tab/search-tab.component';
import {ProjectTabComponent} from './home/project-tab/project-tab.component';
import {AuthGuardService} from './services/auth-guard.service';
import {LoginComponent} from './login/login.component';
const routes: Routes = [
  {path: 'projects', component: ProjectTabComponent, canActivate: [AuthGuardService]},
  {path: 'search', component: SearchTabComponent, canActivate: [AuthGuardService]},
  {path: 'login', component: LoginComponent},
  {path: '**', redirectTo: '/login', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SearchTabComponent} from './components/search-tab/search-tab.component';
import {NewResourceTabComponent} from './components/new-resource-tab/new-resource-tab.component';
import {AuthGuardService} from './services/auth-guard.service';
import {LoginComponent} from './components/misc/login/login.component';
import {PublishTabComponent} from './components/publish-tab/publish-tab.component';

const routes: Routes = [
  {path: 'projects', component: NewResourceTabComponent, canActivate: [AuthGuardService]},
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

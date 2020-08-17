import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SearchTabComponent} from './components/search-tab/search-tab.component';
import {NewResourceTabComponent} from './components/new-resource-tab/new-resource-tab.component';
import {AuthGuardService} from './services/auth-guard.service';
import {LoginTabComponent} from './components/login-tab/login-tab.component';
import {PublishTabComponent} from './components/publish-tab/publish-tab.component';
import {ContactTabComponent} from './components/contact-tab/contact-tab.component';

const routes: Routes = [
  {path: 'projects', component: NewResourceTabComponent, canActivate: [AuthGuardService]},
  {path: 'search', component: SearchTabComponent, canActivate: [AuthGuardService]},
  {path: 'publish', component: PublishTabComponent, canActivate: [AuthGuardService]},
  {path: 'login', component: LoginTabComponent},
  {path: 'contact', component: ContactTabComponent},
  {path: '**', redirectTo: '/login', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

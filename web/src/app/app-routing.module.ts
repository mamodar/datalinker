import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {SearchTabComponent} from './home/search-tab/search-tab.component';
import {ProjectTabComponent} from './home/project-tab/project-tab.component';

const routes: Routes = [
  {path: 'projects', component: ProjectTabComponent},
  {path: 'search', component: SearchTabComponent},
  {path: '', redirectTo: '/projects', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

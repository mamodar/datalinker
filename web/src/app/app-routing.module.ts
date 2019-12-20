import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ConnectorViewComponent} from './views/connector-view/connector-view.component';
import {ProjectViewComponent} from './views/project-view/project-view.component';
import {ResourceViewComponent} from './views/resource-view/resource-view.component';

const routes: Routes = [
  {path: '', redirectTo: '/connector', pathMatch: 'full'},
  {path: 'connector', component: ConnectorViewComponent},
  {path: 'project', component: ProjectViewComponent},
  {path: 'project/:id', component: ProjectViewComponent},
  {path: 'resource', component: ResourceViewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

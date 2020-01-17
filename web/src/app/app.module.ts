import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {MatGridListModule, MatListModule, MatToolbarModule} from '@angular/material';
import { HomeComponent } from './home/home.component';
import { ProjectItemComponent } from './project/project-item/project-item.component';
import { ProjectSearchComponent } from './project/project-search/project-search.component';
import { ProjectListComponent } from './project/project-list/project-list.component';
import { ResourceItemComponent } from './resource/resource-item/resource-item.component';
import { ResourceListComponent } from './resource/resource-list/resource-list.component';
import { ResourceSearchComponent } from './resource/resource-search/resource-search.component';
import { ProjectComponent } from './project/project.component';
import { ResourceComponent } from './resource/resource.component';
import { RelationshipComponent } from './relationship/relationship.component';
import { RelationshipButtonComponent } from './relationship/relationship-button/relationship-button.component';
import { ButtonFilterComponent } from './shared/button-filter/button-filter.component';
import { ButtonInformationComponent } from './shared/button-information/button-information.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ProjectItemComponent,
    ProjectSearchComponent,
    ProjectListComponent,
    ResourceItemComponent,
    ResourceListComponent,
    ResourceSearchComponent,
    ProjectComponent,
    ResourceComponent,
    RelationshipComponent,
    RelationshipButtonComponent,
    ButtonFilterComponent,
    ButtonInformationComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    MatToolbarModule,
    MatGridListModule,
    MatListModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

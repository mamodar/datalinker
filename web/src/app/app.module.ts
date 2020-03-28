import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { HomeComponent } from './home/home.component';
import { ProjectListComponent } from './project/project-list/project-list.component';
import { ResourceListComponent } from './resource/resource-list/resource-list.component';
import { ProjectComponent } from './project/project.component';
import { ResourceComponent } from './resource/resource.component';
import { RelationshipComponent } from './relationship/relationship.component';
import { RelationshipButtonComponent } from './relationship/relationship-button/relationship-button.component';
import { ButtonFilterComponent } from './shared/button-filter/button-filter.component';
import { ButtonInformationComponent } from './shared/button-information/button-information.component';
import { ResourceNewComponent } from './resource/resource-new/resource-new.component';
import { ResourceNewDialogComponent } from './resource/resource-new/resource-new-dialog.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatListModule} from '@angular/material/list';
import {MatTableModule} from '@angular/material/table';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatRadioModule} from '@angular/material/radio';
import {MatSelectModule} from '@angular/material/select';
import {MatDialogModule} from '@angular/material/dialog';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ProjectListComponent,
    ResourceListComponent,
    ProjectComponent,
    ResourceComponent,
    RelationshipComponent,
    RelationshipButtonComponent,
    ButtonFilterComponent,
    ButtonInformationComponent,
    ResourceNewComponent,
    ResourceNewDialogComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    MatToolbarModule,
    MatGridListModule,
    MatListModule,
    MatTableModule,
    MatCheckboxModule,
    MatRadioModule,
    MatSelectModule,
    BrowserAnimationsModule,
    MatDialogModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

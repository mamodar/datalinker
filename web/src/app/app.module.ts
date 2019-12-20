import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {ConnectorViewComponent} from './views/connector-view/connector-view.component';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {
  MatAutocompleteModule,
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDividerModule,
  MatFormFieldModule,
  MatGridListModule,
  MatInputModule,
  MatListModule,
  MatSelectModule,
  MatTabsModule,
  MatToolbarModule
} from '@angular/material';

import {ProjectSearcherComponent} from './interactors/project-searcher/project-searcher.component';
import {UserFieldComponent} from './interactors/user-field/user-field.component';
import {ResourceSearcherComponent} from './interactors/resource-searcher/resource-searcher.component';
import {ProjectViewComponent} from './views/project-view/project-view.component';
import {ProjectVisualizationComponent} from './visualizations/project-visualization/project-visualization.component';
import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {ResourceViewComponent} from './views/resource-view/resource-view.component';
import {ResourceVisualizationComponent} from './visualizations/resource-visualization/resource-visualization.component';
import {RelationshipEditorComponent} from './interactors/relationship-editor/relationship-editor.component';


@NgModule({
  declarations: [
    AppComponent,
    ConnectorViewComponent,
    ProjectSearcherComponent,
    UserFieldComponent,
    ResourceSearcherComponent,
    ProjectViewComponent,
    ProjectVisualizationComponent,
    ResourceViewComponent,
    ResourceVisualizationComponent,
    ResourceSearcherComponent,
    RelationshipEditorComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    NoopAnimationsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatGridListModule,
    MatSelectModule,
    MatAutocompleteModule,
    MatTabsModule,
    MatToolbarModule,
    MatDividerModule,
    MatListModule,
    MatChipsModule,
    HttpClientModule,
    MatCheckboxModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

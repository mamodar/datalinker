import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {HomeComponent} from './home/home.component';

import {ProjectListComponent} from './project-list/project-list.component';

import {ResourceListComponent} from './resource-list/resource-list.component';


import {NewResourceAddButtonComponent} from './new-resource-add/new-resource-add-button.component';
import {NewResourceListComponent} from './new-resource-list/new-resource-list.component';
import {NewResourceAttachButtonComponent} from './new-resource-attach-button/new-resource-attach-button.component';

import {ResourceDeleteButtonComponent} from './shared/resource-delete-button/resource-delete-button.component';

import {MatToolbarModule} from '@angular/material/toolbar';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatListModule} from '@angular/material/list';
import {MatTableModule} from '@angular/material/table';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatRadioModule} from '@angular/material/radio';
import {MatSelectModule} from '@angular/material/select';
import {MatDialogModule} from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule} from '@angular/forms';


import {MatInputModule} from '@angular/material/input';
import {MatTabsModule} from '@angular/material/tabs';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatIconModule} from '@angular/material/icon';
import {MatPaginatorModule} from '@angular/material/paginator';
import {ProjectTabComponent} from './home/project-tab/project-tab.component';
import {SearchTabComponent} from './search-tab/search-tab.component';
import {MatChipsModule} from '@angular/material/chips';
import {RouterHeaderComponent} from './router-header/router-header.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {ResourceDeleteDialogComponent} from './shared/resource-delete-button/resource-delete-dialog.component';
import {LoginComponent} from './login/login.component';
import {LoginInterceptor} from './login.interceptor';
import {ResourceManipulateDialogComponent} from './shared/resource-manipulate-dialog/resource-manipulate-dialog.component';
import {ResourceManipulateButtonComponent} from './shared/resource-edit-button/resource-manipulate-button.component';
import {SearchFilterComponent} from './search-filter/search-filter.component';
import {SearchSearchComponent} from './search-search/search-search.component';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {PublishTabComponent} from './home/publish-tab/publish-tab.component';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {PublishProgressBarComponent} from './publish-progress-bar/publish-progress-bar.component';
import {PublishProcedureButtonComponent} from './publish-procedure-button/publish-procedure-button.component';
import {PublishProjectDialogComponent} from './publish-project-dialog/publish-project-dialog.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ProjectListComponent,
    ResourceListComponent,
    NewResourceAddButtonComponent,
    NewResourceListComponent,
    NewResourceAttachButtonComponent,
    ResourceDeleteButtonComponent,
    ProjectTabComponent,
    SearchTabComponent,
    RouterHeaderComponent,
    ResourceDeleteDialogComponent,
    ResourceManipulateButtonComponent,
    LoginComponent,
    ResourceManipulateDialogComponent,
    SearchFilterComponent,
    SearchSearchComponent,
    PublishTabComponent,
    PublishProgressBarComponent,
    PublishProcedureButtonComponent,
    PublishProjectDialogComponent
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
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatTabsModule,
    MatExpansionModule,
    MatIconModule,
    MatPaginatorModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatSlideToggleModule,
    MatAutocompleteModule,
    MatProgressBarModule,
    MatSnackBarModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: LoginInterceptor, multi: true}, MatDatepickerModule],
  bootstrap: [AppComponent]
})
export class AppModule {
}

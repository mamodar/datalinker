import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {HomeComponent} from './components/misc/home/home.component';

import {ProjectListComponent} from './components/shared/project-list-paginate/project-list/project-list.component';

import {ResourceListComponent} from './components/shared/resource-list/resource-list.component';


import {NewResourceListComponent} from './components/new-resource-tab/new-resource-list/new-resource-list.component';
import {NewResourceAttachComponent} from './components/new-resource-tab/new-resource-attach/new-resource-attach.component';

import {ResourceDeleteButtonComponent} from './components/shared/resource-list/resource-delete-button/resource-delete-button.component';

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
import {NewResourceTabComponent} from './components/new-resource-tab/new-resource-tab.component';
import {SearchTabComponent} from './components/search-tab/search-tab.component';
import {MatChipsModule} from '@angular/material/chips';
import {RouterHeaderComponent} from './components/misc/router-header/router-header.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {ResourceDeleteDialogComponent} from './components/shared/resource-list/resource-delete-button/resource-delete-dialog.component';
import {LoginComponent} from './components/misc/login/login.component';
import {LoginInterceptor} from './services/login.interceptor';
import {ResourceManipulateDialogComponent} from './components/shared/resource-list/resource-manipulate-dialog/resource-manipulate-dialog.component';
import {ResourceManipulateButtonComponent} from './components/shared/resource-list/resource-edit-button/resource-manipulate-button.component';
import {SearchFilterComponent} from './components/search-tab/search-filter/search-filter.component';
import {SearchSearchComponent} from './components/search-tab/search-search/search-search.component';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {PublishTabComponent} from './components/publish-tab/publish-tab.component';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {PublishProgressBarComponent} from './components/publish-tab/publish-progress-bar/publish-progress-bar.component';
import {PublishProcedureComponent} from './components/publish-tab/publish-procedure/publish-procedure.component';
import {PublishProjectDialogComponent} from './components/publish-tab/publish-project-dialog/publish-project-dialog.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {NewResourceAddComponent} from './components/new-resource-tab/new-resource-add/new-resource-add.component';
import {PaginatorComponent} from './components/shared/project-list-paginate/paginator/paginator.component';
import {ProjectListPaginateComponent} from './components/shared/project-list-paginate/project-list-paginate.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ProjectListComponent,
    ResourceListComponent,
    NewResourceAddComponent,
    NewResourceListComponent,
    NewResourceAttachComponent,
    ResourceDeleteButtonComponent,
    NewResourceTabComponent,
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
    PublishProcedureComponent,
    PublishProjectDialogComponent,
    NewResourceAddComponent,
    PaginatorComponent,
    ProjectListPaginateComponent
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

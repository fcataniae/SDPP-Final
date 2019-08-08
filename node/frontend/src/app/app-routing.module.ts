import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UploadFilesComponent } from './components/upload-files/upload-files.component';
import { SharedFilesComponent } from './components/shared-files/shared-files.component';
import { SearchFilesComponent } from "./components/search-files/search-files.component";

const routes: Routes = [
  { path: 'uploads', component : UploadFilesComponent},
  { path: 'shareds', component : SharedFilesComponent},
  { path: 'searchs', component : SearchFilesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

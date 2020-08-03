import {Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {PublicationDTO} from '../models/publicationDTO';
import {StateService} from './state.service';
import {Observable} from 'rxjs';

/**
 * This service implements the API exposed by the backend for all /publication calls.
 * This service should not by called directly by components but is used by {@link StateService}.
 * @author Kyanoush Yahosseini
 */

@Injectable({
  providedIn: 'root'
})

export class PublicationService {

  constructor(private apiService: ApiService) {
  }

  createItemDspace(publicationDTO: PublicationDTO): Observable<any> {
    return this.apiService.post('/publications/items/dspace', publicationDTO);
  }

  uploadBitstreamDspace(uuid: string, publicationDTO: PublicationDTO): Observable<any> {
    console.log('uploadBitstreamDspace' + uuid + publicationDTO.title);
    console.log('uploadBitstreamDspace' + uuid + publicationDTO.files.forEach(value => value.name));
    // this creates a 'multipart/form-data' request
    const formData: FormData = new FormData();
    publicationDTO.files.forEach(value => formData.append('file', value, value.name));
    // add the 'application/json' header, otherwise its 'application/octet-stream' and can't be processed by the backend
    formData.append('item', uuid);
    return this.apiService.postWithProgress('/publications/bitstreams/dspace', formData);
  }


  createItemZenodo(publicationDTO: PublicationDTO): Observable<any> {
    return this.apiService.post('/publications/items/zenodo', publicationDTO);
  }

  uploadBitstreamZenodo(uuid: string, publicationDTO: PublicationDTO): Observable<any> {
    console.log('uploadBitstreamDspace' + uuid + publicationDTO.title);
    console.log('uploadBitstreamDspace' + uuid + publicationDTO.files.forEach(value => value.name));
    // this creates a 'multipart/form-data' request
    const formData: FormData = new FormData();

    publicationDTO.files.forEach(value => formData.append('file', value, value.name));

    // add the 'application/json' header, otherwise its 'application/octet-stream' and can't be processed by the backend
    formData.append('item', uuid);
    return this.apiService.postWithProgress('/publications/bitstreams/zenodo', formData);
  }
}

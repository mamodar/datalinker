import {Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {EdocTransfer} from '../models/edocTransfer';
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

  createItemDspace(edocTransfer: EdocTransfer): Observable<any> {
    return this.apiService.post('/dspace/publications/items', edocTransfer);
  }

  uploadBitstreamDspace(uuid: string, edocTransfer: EdocTransfer): Observable<any> {
    console.log('uploadBitstreamDspace' + uuid + edocTransfer.title);
    console.log('uploadBitstreamDspace' + uuid + edocTransfer.file.name);
    // this creates a 'multipart/form-data' request
    const formData: FormData = new FormData();
    formData.append('file', edocTransfer.file, edocTransfer.file.name);
    // add the 'application/json' header, otherwise its 'application/octet-stream' and can't be processed by the backend
    formData.append('item', uuid);
    return this.apiService.postWithProgress('/dspace/publications/bitstreams', formData);
  }
}
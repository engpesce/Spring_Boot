import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReportDTO } from '../entity/report';
import { SERVER_API_URL } from 'app/shared/app.constants';

@Injectable({ providedIn: 'root' })
export class ProcessCartsService {
    private URL = SERVER_API_URL + '/batch/processCarts';
    private URL_MOCK = './assets/report.json';
    
    constructor(private http: HttpClient) {

    }

    processCarts(): Observable<HttpResponse<ReportDTO>> {
        return this.http.get<ReportDTO>(this.URL, { observe: 'response' });
    }

}
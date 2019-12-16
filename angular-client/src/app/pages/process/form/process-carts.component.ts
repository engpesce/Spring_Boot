import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ReportDTO } from '../entity/report';
import { ProcessCartsService } from '../service/process-carts.service';

@Component({
    selector: 'process-carts-cmp',
    moduleId: module.id,
    templateUrl: 'process-carts.component.html'
})

export class ProcessCartsComponent implements OnInit{
    
    public report: ReportDTO = new ReportDTO();
    public title: string;
    public errorMsg: string;

    constructor(private route: ActivatedRoute,
                private router: Router,
                private processCartsService: ProcessCartsService){
    }

    ngOnInit(){
        
        const id = this.route.snapshot.paramMap.get('id');   

        if (id) {
          /*this.productsCartsService.getById(parseInt(id)).subscribe(resp => {
            this.report = resp;
    
            this.title = 'Reporte de carritos procesados: ' + this.report.id;
          });*/
        } else {
          this.title = 'Procesar Lote de Carritos';
        }
    }

    onSubmit() {

      this.processCartsService.processCarts().subscribe(
        response => {
          this.title = 'Resumen de Carritos Procesados';
          this.report = response.body;
        },
        error => {
          this.errorMsg = 'Ha ocurrido un problema procesando el lote de carritos: ' + error.error;
          console.error(error);
        });

    } 

    cleanNotification(){
      this.errorMsg = undefined;
    }
}

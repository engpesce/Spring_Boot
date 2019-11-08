import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AdminLayoutRoutes } from './admin-layout.routing';

import { DashboardComponent }       from '../../pages/dashboard/dashboard.component';
import { UserComponent }            from '../../pages/user/user.component';
import { TableComponent }           from '../../pages/table/table.component';
import { TypographyComponent }      from '../../pages/typography/typography.component';
import { IconsComponent }           from '../../pages/icons/icons.component';
import { MapsComponent }            from '../../pages/maps/maps.component';
import { NotificationsComponent }   from '../../pages/notifications/notifications.component';
import { UpgradeComponent }         from '../../pages/upgrade/upgrade.component';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProductsComponent } from 'app/pages/products/list/products.component';
import { ProductsFormComponent } from 'app/pages/products/form/products-form.component';
import { CartFormComponent } from 'app/pages/carts/form/cart-form.component';
import { CartDetailComponent } from 'app/pages/carts/detail/cart-detail.component';
import { CartDialogComponent } from 'app/pages/carts/form/cart-dialog.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AdminLayoutRoutes),
    FormsModule,
    NgbModule
  ],
  declarations: [
    DashboardComponent,
    UserComponent,
    TableComponent,
    UpgradeComponent,
    TypographyComponent,
    IconsComponent,
    MapsComponent,
    NotificationsComponent,

    ProductsComponent,
    ProductsFormComponent,
    CartFormComponent,
    CartDetailComponent,
    CartDialogComponent,
  ],
  entryComponents: [ CartDialogComponent ]
})

export class AdminLayoutModule {}

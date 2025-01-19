import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';


import { AppComponent } from './app.component';
import { MainComponent } from './main/main.component';
import { AddTransactionComponent } from './add-transaction/add-transaction.component';
import { AnalysisComponent } from './analysis/analysis.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UserTransactionsComponent } from './user-transactions/user-transactions.component';
import { UserMainComponent } from './user-main/user-main.component';
import { EquipmentComponent } from './equipment/equipment.component';
import { AddEquipmentComponent } from './add-equipment/add-equipment.component';
import { EquipmentTypesComponent } from './equipment-types/equipment-types.component';
import { AddEquipmentTypeComponent } from './add-equipment-type/add-equipment-type.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    AddTransactionComponent,
    AnalysisComponent,
    LoginComponent,
    RegisterComponent,
    UserTransactionsComponent,
    UserMainComponent,
    EquipmentComponent,
    AddEquipmentComponent,
    EquipmentTypesComponent,
    AddEquipmentTypeComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot([
      {path: 'main', component: MainComponent},
      {path: 'add-transaction', component: AddTransactionComponent},
      {path: 'analysis', component: AnalysisComponent},
      {path: 'login', component: LoginComponent},
      {path: 'register', component: RegisterComponent},
      {path: 'user-main', component: UserMainComponent},
      {path: 'equipment', component: EquipmentComponent},
      {path: 'add-equipment', component: AddEquipmentComponent},
      {path: 'equipment-types', component: EquipmentTypesComponent},
      {path: 'add-equipment-type', component: AddEquipmentTypeComponent},
    ]),
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

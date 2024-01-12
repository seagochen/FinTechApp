import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountKeeperComponent } from './account-keeper.component';

describe('AccountKeeperComponent', () => {
  let component: AccountKeeperComponent;
  let fixture: ComponentFixture<AccountKeeperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountKeeperComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccountKeeperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

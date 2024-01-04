import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LedgerKeeperComponent } from './ledger-keeper.component';

describe('LedgerKeeperComponent', () => {
  let component: LedgerKeeperComponent;
  let fixture: ComponentFixture<LedgerKeeperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LedgerKeeperComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LedgerKeeperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

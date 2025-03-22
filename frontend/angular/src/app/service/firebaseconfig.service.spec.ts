import { TestBed } from '@angular/core/testing';

import { FirebaseconfigService } from './firebaseconfig.service';

describe('FirebaseconfigService', () => {
  let service: FirebaseconfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FirebaseconfigService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

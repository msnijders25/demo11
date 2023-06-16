package com.example.demo11;

import com.example.demo11.QueryResolutionForm;
import com.example.demo11.QueryResolutionResult;

public interface QueryResolutionStrategy  {
    QueryResolutionResult resolve(QueryResolutionForm ontvangQuery);
}


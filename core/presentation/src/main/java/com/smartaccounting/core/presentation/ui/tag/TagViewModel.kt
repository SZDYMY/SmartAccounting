package com.smartaccounting.core.presentation.ui.tag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartaccounting.core.domain.model.Tag
import com.smartaccounting.core.domain.usecase.tag.GetTagsUseCase
import com.smartaccounting.core.domain.usecase.tag.ManageTagUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TagUiState(
    val isLoading: Boolean = false,
    val tags: List<Tag> = emptyList(),
    val selectedTag: Tag? = null,
    val error: String? = null
)

class TagViewModel(
    private val getTagsUseCase: GetTagsUseCase,
    private val manageTagUseCase: ManageTagUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(TagUiState())
    val uiState: StateFlow<TagUiState> = _uiState.asStateFlow()
    
    fun loadTags(userId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            getTagsUseCase(userId).collect { tags ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    tags = tags
                )
            }
        }
    }
    
    fun addTag(name: String, color: String, icon: String?, parentId: String?, userId: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val tag = Tag(
                userId = userId,
                name = name,
                color = color,
                icon = icon,
                parentId = parentId,
                isSystem = false
            )
            manageTagUseCase.addTag(tag)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
                }
        }
    }
    
    fun updateTag(tag: Tag) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            manageTagUseCase.updateTag(tag)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
                }
        }
    }
    
    fun deleteTag(tagId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            manageTagUseCase.deleteTag(tagId)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
                }
        }
    }
}
